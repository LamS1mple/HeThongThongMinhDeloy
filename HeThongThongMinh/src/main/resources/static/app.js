const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
    });
};

stompClient.onWebSocketError = (error) => {
	console
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
}
connect()
function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName(frame) {
    
}



const videoElement = document.querySelector('#localVideo');
const canvas = document.querySelector('#canvas');
      const context = canvas.getContext('2d');

navigator.mediaDevices.getUserMedia({ video: true  })
  .then((stream) => {
    videoElement.srcObject = stream;
    // Xử lý video frame và gửi chúng qua WebSocket
  

     
      setInterval(captureAndSendFrame, 1000); // Cứ sau 100ms cắt và gửi frame
  
  })
  .catch((error) => {
    console.error('Không thể truy cập webcam: ' + error);
  });
  
async function captureAndSendFrame  () {
          // Đặt kích thước canvas để phù hợp với video
          canvas.width = videoElement.videoWidth;
          canvas.height = videoElement.videoHeight;

          // Vẽ frame hiện tại từ video lên canvas
          context.drawImage(videoElement, 0, 0, canvas.width, canvas.height);

          // Trích xuất dữ liệu hình ảnh từ canvas dưới dạng base64
          const frameDataUrl = canvas.toDataURL('image/jpg');
          
          stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({name:frameDataUrl})
    		});
			console.log(frameDataUrl)
		   
		   await fetch("http://localhost:8080/thu", {
        method:"POST",
        body:JSON.stringify({name:frameDataUrl})
        
    })
        
};
