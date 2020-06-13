import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

public class Server {
    private static int getFileSize(String filepath) {
        return (int) new File(filepath).length();
    }

    private static byte[] getFileData(String filepath) throws IOException {
        File file = new File(filepath);
        if (!file.exists() || file.isDirectory()) {
            return new byte[0];
        }
        byte[] data = new byte[getFileSize(filepath)];

        FileInputStream fileInputStream = new FileInputStream(file);
        int readBytes = fileInputStream.read(data);
        System.out.println("Read " + readBytes + " B from the file " + filepath);
        fileInputStream.close();
        return data;
    }

    private static String getFilePath(String filename) {
        String root = "src/main/web";
        if (filename.equals("/")) {
            return root + "/index.html";
        } else {
            return root + "/" + filename;
        }
    }

    private static HashMap<String, String> getRequestHeaders(Socket socket) throws IOException {
        HashMap<String, String> requestHeaders = new HashMap<>();
        InputStream inputStream = socket.getInputStream();

        while (true) {
            String line = readLine(inputStream);
            if (line != null) {
                if (line.trim().length() == 0) {
                    return requestHeaders;
                }
                if (line.startsWith("GET") || line.startsWith("POST")) {
                    String key = line.split(" ")[0].trim();
                    String value = line.split(" ")[1].trim();
                    requestHeaders.put(key, value);
                } else {
                    int colon = line.indexOf(":");
                    String key = line.substring(0, colon).trim();
                    String value = line.substring(colon + 1).trim();
                    requestHeaders.put(key, value);
                }
            }
        }
    }

    private static String readLine(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean isFirstLine = true;

        while (true) {
            int readByte = inputStream.read();
            if (readByte == -1) {
                if (isFirstLine) {
                    return null;
                }
                return sb.toString();
            } else if (readByte == 10) {
                return sb.toString();
            } else {
                sb.append((char) readByte);
                isFirstLine = false;
            }
        }
    }

    private static void sendRequestedFile(Socket socket, String requestedFile) throws IOException {
        String filepath = getFilePath(requestedFile);
        int fileLength = getFileSize(filepath);

        // Write the resp headers

        // Send the resp headers
        OutputStream dataOut = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(dataOut);
        String resp = "HTTP/1.1 200 OK" + "\n" +
                "Content-Type:" + getContentType(filepath) + "\n" +
                "Content-Length:" + fileLength + "\n\n";
        printWriter.write(resp);
        printWriter.flush();

        // Send the data
        byte[] data = getFileData(filepath);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(dataOut);
        bufferedOutputStream.write(data);
        bufferedOutputStream.flush();

        // Close the streams // socket
        socket.close();
    }

    private static String getContentType(String file) {
        if (file.endsWith(".html")) {
            return "text/html";
        } else if (file.endsWith(".js")) {
            return "text/javascript";
        } else if (file.endsWith(".css")) {
            return "text/css";
        }
        return "";
    }

    private static void connectToWebSocket(Socket socket, HashMap<String, String> headers) throws NoSuchAlgorithmException, IOException {
        System.out.println("WebSocket connection ... ");

        // HandShake
        final String KEY = headers.get("Sec-WebSocket-Key");
        System.out.println("KEY : " + KEY);
        final String CODE = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        final String RES_KEY = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest((KEY + CODE).getBytes()));
        System.out.println("RES_KEY : " + RES_KEY);
        OutputStream dataOut = socket.getOutputStream();

        // Write the resp headers
        String resp = "HTTP/1.1 101 Switching Protocols" + "\r\n" +
                "Connection:Upgrade" + "\r\n" +
                "Upgrade:websocket" + "\r\n" +
                "Sec-WebSocket-Accept:" + RES_KEY + "\r\n\n";
        PrintWriter printWriter = new PrintWriter(dataOut);
        printWriter.write(resp);
        printWriter.flush();

        // send and receive messages
        webSocket(socket);
    }

    private static int[] getPayloadLength(int payloadLength) {
        // If the length {0 -> 125 } bytes then stick the length in the second byte
        // If the length is {126 -> 2^16} bytes put 126 in the 7 bits then use the following 16bits to represent the length
        // If the length is {2^16 + 1 -> 2^64} put 127 in the 7 bites then use the following 64bits to represent the length
        if (payloadLength <= 125) {
            return new int[]{payloadLength};
        } else if (payloadLength <= Math.pow(2, 16)) {
            String bin16 = getDigitInBinary(payloadLength, 16);
            return new int[]{
                    126,
                    Integer.parseInt(bin16.substring(0, 8), 2),
                    Integer.parseInt(bin16.substring(8), 2)};
        } else if (payloadLength > Math.pow(2, 16)) {
            String bin64 = getDigitInBinary(payloadLength, 64);
            return new int[]{
                    127,
                    Integer.parseInt(bin64.substring(0, 8), 2),
                    Integer.parseInt(bin64.substring(8, 16), 2),
                    Integer.parseInt(bin64.substring(16, 24), 2),
                    Integer.parseInt(bin64.substring(24, 32), 2),
                    Integer.parseInt(bin64.substring(32, 40), 2),
                    Integer.parseInt(bin64.substring(40, 48), 2),
                    Integer.parseInt(bin64.substring(48, 56), 2),
                    Integer.parseInt(bin64.substring(56), 2),
            };
        }
        return new int[0];
    }

    private static void sendMessage(String message, Socket socket) throws IOException {
        // Aways 129 if sending text
        int firstByte = 129;

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(firstByte);

        File file = new File("src/main/java/Server.java");
        // Writing the payload length
        for (int b : getPayloadLength(((int) file.length()))) {
            outputStream.write(b);
        }
        byte[] data = new byte[(int) file.length()];
        FileInputStream inputStream = new FileInputStream(file);
        int readBytes = inputStream.read(data);
        System.out.println("Read data of length :: " + readBytes + " from the file " + file.getAbsolutePath() + " of length " + file.length());
        outputStream.write(data);
        outputStream.flush();

    }

    private static void webSocket(Socket socket) throws IOException {
        // try and send data
        sendMessage(null, socket);

        InputStream inputStream = socket.getInputStream();
        int bt = inputStream.read();
        System.out.println("First Byte == " + bt);
        String bits = Integer.toBinaryString(bt);
        String Opcode = bits.substring(bits.length() - 4);
        System.out.println("Opcode == " + Opcode);

        // Get the payloadLength
        // test the second bit
        int payloadLength;
        int secondByte = inputStream.read();
        int dif = secondByte - 128;
        if (dif >= 0 && dif <= 125) {
            payloadLength = dif;
        } else if (dif == 126) {
            payloadLength = getPayloadLength(
                    inputStream.read(), // 0
                    inputStream.read()); // 1
        } else if (dif == 127) {
            payloadLength = getPayloadLength(
                    inputStream.read(), // 0
                    inputStream.read(), // 1
                    inputStream.read(), // 2
                    inputStream.read(), // 3
                    inputStream.read(), // 4
                    inputStream.read(), // 5
                    inputStream.read(), // 6
                    inputStream.read()); // 7
        } else {
            throw new RuntimeException("Error in payload Length diff == " + dif);
        }
//        System.out.println("PayloadLength == " + payloadLength);
        // Get the decodeKeys
        byte[] decodeKeys = new byte[]{
                (byte) inputStream.read(),
                (byte) inputStream.read(),
                (byte) inputStream.read(),
                (byte) inputStream.read()
        };

        byte[] data = new byte[payloadLength];
        for (int i = 0; i < payloadLength; i++) {
            byte b = (byte) inputStream.read();
            data[i] = b;
//            System.out.println("BYTE no : " + i + " == " + b);
        }

        byte[] decodedData = getDecodedMessage(decodeKeys, data);
//        FileOutputStream fileOutputStream = new FileOutputStream("src/main/java/img.jpg");
//        fileOutputStream.write(decodedData);
//        fileOutputStream.flush();
//        System.out.println("Done Writing to the file");
//        fileOutputStream.close();
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < payloadLength; i++) {
            msg.append((char) decodedData[i]);
        }
        System.out.println("MESSAGE == " + msg);
    }


    private static int getPayloadLength(int... bytes) {
        StringBuilder sb = new StringBuilder();
        System.out.println("BytesLength :: " + bytes.length);
        for (int i : bytes) {
            sb.append(getDigitInBinary(i, 8));
        }
        return Integer.parseInt(sb.toString(), 2);
    }

    private static byte[] getDecodedMessage(byte[] decodeKeys, byte[] data) {
        byte[] decodedData = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            decodedData[i] = (byte) (data[i] ^ decodeKeys[i & 0x3]);
        }
        return decodedData;
    }

    private static String getDigitInBinary(int bt, int bits) {
        String b = Integer.toBinaryString(bt);
        String add = "";
        if (b.length() != bits) {
            for (int i = 0; i < bits - b.length(); i++) {
                add = add.concat("0");
            }
        }
        return add + b;
    }

    public static void main(String[] args) throws IOException {
//        System.out.println(getDigitInBinary(126, 8));
        final int PORT = 5000;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Error connecting to the port " + PORT);
            return;
        }
        System.out.println("Server Started @port " + PORT);

        while (true) {
            // Blocking
            Socket socket = serverSocket.accept();
            System.out.println("Client connected ... " + socket.getRemoteSocketAddress());

            new Thread(() -> {
                try {
                    // Get the Http Headers
                    HashMap<String, String> requestHeaders = getRequestHeaders(socket);
                    if (requestHeaders.containsKey("Sec-WebSocket-Key")) {
                        connectToWebSocket(socket, requestHeaders);
                        return;
                    }
                    String request = requestHeaders.get("GET");
                    System.out.println("Request :: " + request);
                    sendRequestedFile(socket, request);

                    socket.close();
                } catch (IOException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }


}
