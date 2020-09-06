const inputButton = document.querySelector("button");
const inputText = document.querySelector("#textInput");
const webSocket = new WebSocket(`ws://${location.host}`);

webSocket.onopen = () => {
  console.log("WebSocket is Open 😀😀");

  inputButton.addEventListener("click", () => {
    const message = inputText.value;
    if (message.trim()) {
      console.log("Message sent == " + message);
      webSocket.send(message);
      inputText.value = "";
    }
  });
};

webSocket.onerror = (e) => {
  console.error("Error in webSocket 😥😥", e);
};

webSocket.onclose = () => {
  console.log("WebSocket clossed");
};
