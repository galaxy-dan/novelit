function test() {

  const senderUUID = "chamhwag1"

  const eventSource = new EventSource(
      `http://localhost:8003/notifications/subscribe?senderUUID=${senderUUID}`
  );

  eventSource.addEventListener("alertComment", (event) => {
    console.log(event);

    //const data = JSON.parse(event.data);

    // 크롬 알림
   /* (async () => {
      const showNotification = () => {
        const notification = new Notification("header : 응 헤더야", {
          body: data.content,
        });
        setTimeout(() => {
          notification.close();
        }, 10 * 1000);

        notification.addEventListener("click", () => {
          window.open(data.url, "_blank");
        });
      };

      let granted = false;

      if (Notification.permission === "granted") {
        granted = true;
      } else if (Notification.permission !== "denied") {
        let permission = await Notification.requestPermission();
        granted = permission === "granted";
      }

      // 알림 보여주기
      if (granted) {
        showNotification();
      }
    })();*/
  });
}

function test2(){
  const senderUUID = "chamhwag1"

  const eventSource = new EventSource(
      `http://localhost:8003/notifications/send-data?senderUUID=${senderUUID}`
  );

  eventSource.addEventListener("alertComment", (event) => {
    console.log(event);
  });
}