public class Notes{
    public static final String MESSAGE = "            \"        }\\n\" +\n" +
            "            \"        System.out.println(\\\"PayloadLength == \\\" + payloadLength);\\n\" +\n" +
            "            \"        // Get the decodeKeys\\n\" +\n" +
            "            \"        byte[] decodeKeys = new byte[]{\\n\" +\n" +
            "            \"                (byte) inputStream.read(),\\n\" +\n" +
            "            \"                (byte) inputStream.read(),\\n\" +\n" +
            "            \"                (byte) inputStream.read(),\\n\" +\n" +
            "            \"                (byte) inputStream.read()\\n\" +\n" +
            "            \"        };\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        byte[] data = new byte[payloadLength];\\n\" +\n" +
            "            \"        for (int i = 0; i < payloadLength; i++) {\\n\" +\n" +
            "            \"            byte b = (byte) inputStream.read();\\n\" +\n" +
            "            \"            data[i] = b;\\n\" +\n" +
            "            \"//            System.out.println(\\\"BYTE no : \\\" + i + \\\" == \\\" + b);\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        byte[] decodedData = getDecodedMessage(decodeKeys, data);\\n\" +\n" +
            "            \"//        FileOutputStream fileOutputStream = new FileOutputStream(\\\"src/main/java/img.jpg\\\");\\n\" +\n" +
            "            \"//        fileOutputStream.write(decodedData);\\n\" +\n" +
            "            \"//        fileOutputStream.flush();\\n\" +\n" +
            "            \"//        System.out.println(\\\"Done Writing to the file\\\");\\n\" +\n" +
            "            \"//        fileOutputStream.close();\\n\" +\n" +
            "            \"//        StringBuilder msg = new StringBuilder();\\n\" +\n" +
            "            \"//        for (int i = 0; i < payloadLength; i++) {\\n\" +\n" +
            "            \"//            msg.append((char) decodedData[i]);\\n\" +\n" +
            "            \"//        }\\n\" +\n" +
            "            \"//        System.out.println(\\\"MESSAGE == \\\" + msg);\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static int getPayloadLength(int... bytes) {\\n\" +\n" +
            "            \"        StringBuilder sb = new StringBuilder();\\n\" +\n" +
            "            \"        System.out.println(\\\"BytesLength :: \\\" + bytes.length);\\n\" +\n" +
            "            \"        for (int i : bytes) {\\n\" +\n" +
            "            \"            sb.append(getDigitInBinary(i, 8));\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        return Integer.parseInt(sb.toString(), 2);\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static byte[] getDecodedMessage(byte[] decodeKeys, byte[] data) {\\n\" +\n" +
            "            \"        byte[] decodedData = new byte[data.length];\\n\" +\n" +
            "            \"        for (int i = 0; i < data.length; i++) {\\n\" +\n" +
            "            \"            decodedData[i] = (byte) (data[i] ^ decodeKeys[i & 0x3]);\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        return decodedData;\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static String getDigitInBinary(int bt, int bits) {\\n\" +\n" +
            "            \"        String b = Integer.toBinaryString(bt);\\n\" +\n" +
            "            \"        String add = \\\"\\\";\\n\" +\n" +
            "            \"        if (b.length() != bits) {\\n\" +\n" +
            "            \"            for (int i = 0; i < bits - b.length(); i++) {\\n\" +\n" +
            "            \"                add = add.concat(\\\"0\\\");\\n\" +\n" +
            "            \"            }\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        return add + b;\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    public static void main(String[] args) throws IOException {\\n\" +\n" +
            "            \"        System.out.println(getDigitInBinary(126, 8));\\n\" +\n" +
            "            \"        final int PORT = 5000;\\n\" +\n" +
            "            \"        ServerSocket serverSocket;\\n\" +\n" +
            "            \"        try {\\n\" +\n" +
            "            \"            serverSocket = new ServerSocket(PORT);\\n\" +\n" +
            "            \"        } catch (IOException e) {\\n\" +\n" +
            "            \"            System.err.println(\\\"Error connecting to the port \\\" + PORT);\\n\" +\n" +
            "            \"            return;\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        System.out.println(\\\"Server Started @port \\\" + PORT);\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        while (true) {\\n\" +\n" +
            "            \"            // Blocking\\n\" +\n" +
            "            \"            Socket socket = serverSocket.accept();\\n\" +\n" +
            "            \"            System.out.println(\\\"Client connected ... \\\" + socket.getRemoteSocketAddress());\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"            new Thread(() -> {\\n\" +\n" +
            "            \"                try {\\n\" +\n" +
            "            \"                    // Get the Http Headers\\n\" +\n" +
            "            \"                    HashMap<String, String> requestHeaders = getRequestHeaders(socket);\\n\" +\n" +
            "            \"                    if (requestHeaders.containsKey(\\\"Sec-WebSocket-Key\\\")) {\\n\" +\n" +
            "            \"                        connectToWebSocket(socket, requestHeaders);\\n\" +\n" +
            "            \"                        return;\\n\" +\n" +
            "            \"                    }\\n\" +\n" +
            "            \"                    String request = requestHeaders.get(\\\"GET\\\");\\n\" +\n" +
            "            \"                    System.out.println(\\\"Request :: \\\" + request);\\n\" +\n" +
            "            \"                    sendRequestedFile(socket, request);\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"                    socket.close();\\n\" +\n" +
            "            \"                } catch (IOException | NoSuchAlgorithmException e) {\\n\" +\n" +
            "            \"                    e.printStackTrace();\\n\" +\n" +
            "            \"                }\\n\" +\n" +
            "            \"            }).start();\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"}\\n\" +\n" +
            "            \"import java.io.*;\\n\" +\n" +
            "            \"import java.net.ServerSocket;\\n\" +\n" +
            "            \"import java.net.Socket;\\n\" +\n" +
            "            \"import java.security.MessageDigest;\\n\" +\n" +
            "            \"import java.security.NoSuchAlgorithmException;\\n\" +\n" +
            "            \"import java.util.Base64;\\n\" +\n" +
            "            \"import java.util.HashMap;\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"public class Server {\\n\" +\n" +
            "            \"    private static int getFileSize(String filepath) {\\n\" +\n" +
            "            \"        return (int) new File(filepath).length();\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static byte[] getFileData(String filepath) throws IOException {\\n\" +\n" +
            "            \"        File file = new File(filepath);\\n\" +\n" +
            "            \"        if (!file.exists() || file.isDirectory()) {\\n\" +\n" +
            "            \"            return new byte[0];\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        byte[] data = new byte[getFileSize(filepath)];\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        FileInputStream fileInputStream = new FileInputStream(file);\\n\" +\n" +
            "            \"        int readBytes = fileInputStream.read(data);\\n\" +\n" +
            "            \"        System.out.println(\\\"Read \\\" + readBytes + \\\" B from the file \\\" + filepath);\\n\" +\n" +
            "            \"        fileInputStream.close();\\n\" +\n" +
            "            \"        return data;\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static String getFilePath(String filename) {\\n\" +\n" +
            "            \"        String root = \\\"src/main/web\\\";\\n\" +\n" +
            "            \"        if (filename.equals(\\\"/\\\")) {\\n\" +\n" +
            "            \"            return root + \\\"/index.html\\\";\\n\" +\n" +
            "            \"        } else {\\n\" +\n" +
            "            \"            return root + \\\"/\\\" + filename;\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static HashMap<String, String> getRequestHeaders(Socket socket) throws IOException {\\n\" +\n" +
            "            \"        HashMap<String, String> requestHeaders = new HashMap<>();\\n\" +\n" +
            "            \"        InputStream inputStream = socket.getInputStream();\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        while (true) {\\n\" +\n" +
            "            \"            String line = readLine(inputStream);\\n\" +\n" +
            "            \"            if (line != null) {\\n\" +\n" +
            "            \"                if (line.trim().length() == 0) {\\n\" +\n" +
            "            \"                    return requestHeaders;\\n\" +\n" +
            "            \"                }\\n\" +\n" +
            "            \"                if (line.startsWith(\\\"GET\\\") || line.startsWith(\\\"POST\\\")) {\\n\" +\n" +
            "            \"                    String key = line.split(\\\" \\\")[0].trim();\\n\" +\n" +
            "            \"                    String value = line.split(\\\" \\\")[1].trim();\\n\" +\n" +
            "            \"                    requestHeaders.put(key, value);\\n\" +\n" +
            "            \"                } else {\\n\" +\n" +
            "            \"                    int colon = line.indexOf(\\\":\\\");\\n\" +\n" +
            "            \"                    String key = line.substring(0, colon).trim();\\n\" +\n" +
            "            \"                    String value = line.substring(colon + 1).trim();\\n\" +\n" +
            "            \"                    requestHeaders.put(key, value);\\n\" +\n" +
            "            \"                }\\n\" +\n" +
            "            \"            }\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static String readLine(InputStream inputStream) throws IOException {\\n\" +\n" +
            "            \"        StringBuilder sb = new StringBuilder();\\n\" +\n" +
            "            \"        boolean isFirstLine = true;\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        while (true) {\\n\" +\n" +
            "            \"            int readByte = inputStream.read();\\n\" +\n" +
            "            \"            if (readByte == -1) {\\n\" +\n" +
            "            \"                if (isFirstLine) {\\n\" +\n" +
            "            \"                    return null;\\n\" +\n" +
            "            \"                }\\n\" +\n" +
            "            \"                return sb.toString();\\n\" +\n" +
            "            \"            } else if (readByte == 10) {\\n\" +\n" +
            "            \"                return sb.toString();\\n\" +\n" +
            "            \"            } else {\\n\" +\n" +
            "            \"                sb.append((char) readByte);\\n\" +\n" +
            "            \"                isFirstLine = false;\\n\" +\n" +
            "            \"            }\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static void sendRequestedFile(Socket socket, String requestedFile) throws IOException {\\n\" +\n" +
            "            \"        String filepath = getFilePath(requestedFile);\\n\" +\n" +
            "            \"        int fileLength = getFileSize(filepath);\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        // Write the resp headers\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        // Send the resp headers\\n\" +\n" +
            "            \"        OutputStream dataOut = socket.getOutputStream();\\n\" +\n" +
            "            \"        PrintWriter printWriter = new PrintWriter(dataOut);\\n\" +\n" +
            "            \"        String resp = \\\"HTTP/1.1 200 OK\\\" + \\\"\\\\n\\\" +\\n\" +\n" +
            "            \"                \\\"Content-Type:\\\" + getContentType(filepath) + \\\"\\\\n\\\" +\\n\" +\n" +
            "            \"                \\\"Content-Length:\\\" + fileLength + \\\"\\\\n\\\\n\\\";\\n\" +\n" +
            "            \"        printWriter.write(resp);\\n\" +\n" +
            "            \"        printWriter.flush();\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        // Send the data\\n\" +\n" +
            "            \"        byte[] data = getFileData(filepath);\\n\" +\n" +
            "            \"        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(dataOut);\\n\" +\n" +
            "            \"        bufferedOutputStream.write(data);\\n\" +\n" +
            "            \"        bufferedOutputStream.flush();\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        // Close the streams // socket\\n\" +\n" +
            "            \"        socket.close();\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static String getContentType(String file) {\\n\" +\n" +
            "            \"        if (file.endsWith(\\\".html\\\")) {\\n\" +\n" +
            "            \"            return \\\"text/html\\\";\\n\" +\n" +
            "            \"        } else if (file.endsWith(\\\".js\\\")) {\\n\" +\n" +
            "            \"            return \\\"text/javascript\\\";\\n\" +\n" +
            "            \"        } else if (file.endsWith(\\\".css\\\")) {\\n\" +\n" +
            "            \"            return \\\"text/css\\\";\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        return \\\"\\\";\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static void connectToWebSocket(Socket socket, HashMap<String, String> headers) throws NoSuchAlgorithmException, IOException {\\n\" +\n" +
            "            \"        System.out.println(\\\"WebSocket connection ... \\\");\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        // HandShake\\n\" +\n" +
            "            \"        final String KEY = headers.get(\\\"Sec-WebSocket-Key\\\");\\n\" +\n" +
            "            \"        System.out.println(\\\"KEY : \\\" + KEY);\\n\" +\n" +
            "            \"        final String CODE = \\\"258EAFA5-E914-47DA-95CA-C5AB0DC85B11\\\";\\n\" +\n" +
            "            \"        final String RES_KEY = Base64.getEncoder().encodeToString(MessageDigest.getInstance(\\\"SHA-1\\\").digest((KEY + CODE).getBytes()));\\n\" +\n" +
            "            \"        System.out.println(\\\"RES_KEY : \\\" + RES_KEY);\\n\" +\n" +
            "            \"        OutputStream dataOut = socket.getOutputStream();\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        // Write the resp headers\\n\" +\n" +
            "            \"        String resp = \\\"HTTP/1.1 101 Switching Protocols\\\" + \\\"\\\\r\\\\n\\\" +\\n\" +\n" +
            "            \"                \\\"Connection:Upgrade\\\" + \\\"\\\\r\\\\n\\\" +\\n\" +\n" +
            "            \"                \\\"Upgrade:websocket\\\" + \\\"\\\\r\\\\n\\\" +\\n\" +\n" +
            "            \"                \\\"Sec-WebSocket-Accept:\\\" + RES_KEY + \\\"\\\\r\\\\n\\\\n\\\";\\n\" +\n" +
            "            \"        PrintWriter printWriter = new PrintWriter(dataOut);\\n\" +\n" +
            "            \"        printWriter.write(resp);\\n\" +\n" +
            "            \"        printWriter.flush();\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        // send and receive messages\\n\" +\n" +
            "            \"        webSocket(socket);\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static int[] getPayloadLength(String message) {\\n\" +\n" +
            "            \"        // If the length {0 -> 125 } bytes then stick the length in the second byte\\n\" +\n" +
            "            \"        // If the length is {126 -> 2^16} bytes put 126 in the 7 bits then use the following 16bits to represent the length\\n\" +\n" +
            "            \"        // If the length is {2^16 + 1 -> 2^64} put 127 in the 7 bites then use the following 64bits to represent the length\\n\" +\n" +
            "            \"        int payloadLength = message.length();\\n\" +\n" +
            "            \"        if (payloadLength <= 125) {\\n\" +\n" +
            "            \"            return new int[]{payloadLength};\\n\" +\n" +
            "            \"        } else if (payloadLength < Math.pow(2, 16)) {\\n\" +\n" +
            "            \"            String bin16 = getDigitInBinary(payloadLength, 16);\\n\" +\n" +
            "            \"            String first = bin16.substring(0, 7);\\n\" +\n" +
            "            \"            String second = bin16.substring(8);\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"            System.out.println(\\\"bin16 == \\\" + bin16);\\n\" +\n" +
            "            \"            System.out.println(\\\"First == \\\" + first);\\n\" +\n" +
            "            \"            System.out.println(\\\"Second == \\\" + second);\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"            return new int[]{\\n\" +\n" +
            "            \"                    126,\\n\" +\n" +
            "            \"                    Integer.parseInt(first, 2),\\n\" +\n" +
            "            \"                    Integer.parseInt(second, 2)};\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        return new int[0];\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static void sendMessage(String message, Socket socket) throws IOException {\\n\" +\n" +
            "            \"        // Aways 129 if sending text\\n\" +\n" +
            "            \"        int firstByte = 129;\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        OutputStream outputStream = socket.getOutputStream();\\n\" +\n" +
            "            \"        outputStream.write(firstByte);\\n\" +\n" +
            "            \"        // Writing the payload length\\n\" +\n" +
            "            \"        for (int b : getPayloadLength(message)) {\\n\" +\n" +
            "            \"            outputStream.write(b);\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        outputStream.write(message.getBytes());\\n\" +\n" +
            "            \"        outputStream.flush();\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static void webSocket(Socket socket) throws IOException {\\n\" +\n" +
            "            \"        // try and send data\\n\" +\n" +
            "            \"        sendMessage(\\\"Hey Bro ... 1\\\", socket);\\n\" +\n" +
            "            \"        sendMessage(\\\"Hey Bro ... 2\\\", socket);\\n\" +\n" +
            "            \"        sendMessage(\\\"Hey Bro ... 3\\\", socket);\\n\" +\n" +
            "            \"        sendMessage(\\\"Hey Bro ... 4\\\", socket);\\n\" +\n" +
            "            \"        sendMessage(\\\"Hey Bro ... 5\\\", socket);\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        InputStream inputStream = socket.getInputStream();\\n\" +\n" +
            "            \"        int bt = inputStream.read();\\n\" +\n" +
            "            \"        System.out.println(\\\"First Byte == \\\" + bt);\\n\" +\n" +
            "            \"        String bits = Integer.toBinaryString(bt);\\n\" +\n" +
            "            \"        String Opcode = bits.substring(bits.length() - 4);\\n\" +\n" +
            "            \"        System.out.println(\\\"Opcode == \\\" + Opcode);\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        // Get the payloadLength\\n\" +\n" +
            "            \"        // test the second bit\\n\" +\n" +
            "            \"        int payloadLength;\\n\" +\n" +
            "            \"        int secondByte = inputStream.read();\\n\" +\n" +
            "            \"        int dif = secondByte - 128;\\n\" +\n" +
            "            \"        if (dif >= 0 && dif <= 125) {\\n\" +\n" +
            "            \"            payloadLength = dif;\\n\" +\n" +
            "            \"        } else if (dif == 126) {\\n\" +\n" +
            "            \"            payloadLength = getPayloadLength(\\n\" +\n" +
            "            \"                    inputStream.read(), // 0\\n\" +\n" +
            "            \"                    inputStream.read()); // 1\\n\" +\n" +
            "            \"        } else if (dif == 127) {\\n\" +\n" +
            "            \"            payloadLength = getPayloadLength(\\n\" +\n" +
            "            \"                    inputStream.read(), // 0\\n\" +\n" +
            "            \"                    inputStream.read(), // 1\\n\" +\n" +
            "            \"                    inputStream.read(), // 2\\n\" +\n" +
            "            \"                    inputStream.read(), // 3\\n\" +\n" +
            "            \"                    inputStream.read(), // 4\\n\" +\n" +
            "            \"                    inputStream.read(), // 5\\n\" +\n" +
            "            \"                    inputStream.read(), // 6\\n\" +\n" +
            "            \"                    inputStream.read()); // 7\\n\" +\n" +
            "            \"        } else {\\n\" +\n" +
            "            \"            throw new RuntimeException(\\\"Error in payload Length diff == \\\" + dif);\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        System.out.println(\\\"PayloadLength == \\\" + payloadLength);\\n\" +\n" +
            "            \"        // Get the decodeKeys\\n\" +\n" +
            "            \"        byte[] decodeKeys = new byte[]{\\n\" +\n" +
            "            \"                (byte) inputStream.read(),\\n\" +\n" +
            "            \"                (byte) inputStream.read(),\\n\" +\n" +
            "            \"                (byte) inputStream.read(),\\n\" +\n" +
            "            \"                (byte) inputStream.read()\\n\" +\n" +
            "            \"        };\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        byte[] data = new byte[payloadLength];\\n\" +\n" +
            "            \"        for (int i = 0; i < payloadLength; i++) {\\n\" +\n" +
            "            \"            byte b = (byte) inputStream.read();\\n\" +\n" +
            "            \"            data[i] = b;\\n\" +\n" +
            "            \"//            System.out.println(\\\"BYTE no : \\\" + i + \\\" == \\\" + b);\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        byte[] decodedData = getDecodedMessage(decodeKeys, data);\\n\" +\n" +
            "            \"//        FileOutputStream fileOutputStream = new FileOutputStream(\\\"src/main/java/img.jpg\\\");\\n\" +\n" +
            "            \"//        fileOutputStream.write(decodedData);\\n\" +\n" +
            "            \"//        fileOutputStream.flush();\\n\" +\n" +
            "            \"//        System.out.println(\\\"Done Writing to the file\\\");\\n\" +\n" +
            "            \"//        fileOutputStream.close();\\n\" +\n" +
            "            \"//        StringBuilder msg = new StringBuilder();\\n\" +\n" +
            "            \"//        for (int i = 0; i < payloadLength; i++) {\\n\" +\n" +
            "            \"//            msg.append((char) decodedData[i]);\\n\" +\n" +
            "            \"//        }\\n\" +\n" +
            "            \"//        System.out.println(\\\"MESSAGE == \\\" + msg);\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static int getPayloadLength(int... bytes) {\\n\" +\n" +
            "            \"        StringBuilder sb = new StringBuilder();\\n\" +\n" +
            "            \"        System.out.println(\\\"BytesLength :: \\\" + bytes.length);\\n\" +\n" +
            "            \"        for (int i : bytes) {\\n\" +\n" +
            "            \"            sb.append(getDigitInBinary(i, 8));\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        return Integer.parseInt(sb.toString(), 2);\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static byte[] getDecodedMessage(byte[] decodeKeys, byte[] data) {\\n\" +\n" +
            "            \"        byte[] decodedData = new byte[data.length];\\n\" +\n" +
            "            \"        for (int i = 0; i < data.length; i++) {\\n\" +\n" +
            "            \"            decodedData[i] = (byte) (data[i] ^ decodeKeys[i & 0x3]);\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        return decodedData;\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    private static String getDigitInBinary(int bt, int bits) {\\n\" +\n" +
            "            \"        String b = Integer.toBinaryString(bt);\\n\" +\n" +
            "            \"        String add = \\\"\\\";\\n\" +\n" +
            "            \"        if (b.length() != bits) {\\n\" +\n" +
            "            \"            for (int i = 0; i < bits - b.length(); i++) {\\n\" +\n" +
            "            \"                add = add.concat(\\\"0\\\");\\n\" +\n" +
            "            \"            }\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        return add + b;\\n\" +\n" +
            "            \"    }\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"    public static void main(String[] args) throws IOException {\\n\" +\n" +
            "            \"        System.out.println(getDigitInBinary(126, 8));\\n\" +\n" +
            "            \"        final int PORT = 5000;\\n\" +\n" +
            "            \"        ServerSocket serverSocket;\\n\" +\n" +
            "            \"        try {\\n\" +\n" +
            "            \"            serverSocket = new ServerSocket(PORT);\\n\" +\n" +
            "            \"        } catch (IOException e) {\\n\" +\n" +
            "            \"            System.err.println(\\\"Error connecting to the port \\\" + PORT);\\n\" +\n" +
            "            \"            return;\\n\" +\n" +
            "            \"        }\\n\" +\n" +
            "            \"        System.out.println(\\\"Server Started @port \\\" + PORT);\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"        while (true) {\\n\" +\n" +
            "            \"            // Blocking\\n\" +\n" +
            "            \"            Socket socket = serverSocket.accept();\\n\" +\n" +
            "            \"            System.out.println(\\\"Client connected ... \\\" + socket.getRemoteSocketAddress());\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"            new Thread(() -> {\\n\" +\n" +
            "            \"                try {\\n\" +\n" +
            "            \"                    // Get the Http Headers\\n\" +\n" +
            "            \"                    HashMap<String, String> requestHeaders = getRequestHeaders(socket);\\n\" +\n" +
            "            \"                    if (requestHeaders.containsKey(\\\"Sec-WebSocket-Key\\\")) {\\n\" +\n" +
            "            \"                        connectToWebSocket(socket, requestHeaders);\\n\" +\n" +
            "            \"                        return;\\n\" +\n" +
            "            \"                    }\\n\" +\n" +
            "            \"                    String request = requestHeaders.get(\\\"GET\\\");\\n\" +\n" +
            "            \"                    System.out.println(\\\"Request :: \\\" + request);\\n\" +";
}