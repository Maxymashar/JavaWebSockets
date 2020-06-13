const btn = document.querySelector("button");
const fileInput = document.getElementById("file");

btn.addEventListener("click", () => {
  // fileInput.click();
  // fileInput.onchange = () => {
    // const file = fileInput.files[0];
    console.log("Starting webSocket");

    const webSocket = new WebSocket(`ws://${location.host}`);
    webSocket.onopen = () => {
      console.log("Connection open 😎😎😎");

      webSocket.send("new Blob()");
      // console.log("File", file);

      webSocket.onmessage = ({ data }) => {
        console.log("data", data);
      };
    };
  // };
});
