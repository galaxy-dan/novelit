'use client';

import { getConfig, post } from '@/service/api/http';
import { useEffect, useState } from 'react';
import { EventSourcePolyfill, NativeEventSource } from 'event-source-polyfill';
import { toast } from 'react-toastify';
import { getAlarmMessage } from '@/service/alarm';
import { Alarm } from '@/model/alarm';
import { useRouter } from 'next/navigation';
import { useQueryClient } from '@tanstack/react-query';

export default function SSE() {
  const [listening, setListening] = useState(false);
  const [data, setData] = useState({ value: 0, target: 100 });
  const router = useRouter();
  const queryClient = useQueryClient();

  
 
  const CloseButton = ({ closeToast }: any, link: string) => (
    <i
      className="material-icons"
      onClick={() => {
        closeToast();
      }}
    >
      이동
    </i>
  );

  useEffect(() => {
    const accessToken = localStorage.getItem('accessToken');
    if (!accessToken) return;

    //     let eventSource: EventSource | undefined = undefined;
    const EventSource = EventSourcePolyfill || NativeEventSource;
    const eventSource = new EventSource(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/notifications/subscribe`,
      {
        headers: {
          Authorization:
            localStorage && localStorage.getItem('accessToken')
              ? `Bearer ${localStorage.getItem('accessToken')}`
              : '',
        },
        heartbeatTimeout: 1200000,
        // withCredentials: true,
      },
    );

    eventSource.addEventListener('alertComment', function (event: any) {
      console.log(event);

      const data: Alarm = JSON.parse(event.data);
      // const data = event.data;
      // console.log(event);

      if (event.type !== 'connection') {
        const messageData = getAlarmMessage(data);
        toast.info(messageData.message, {
          autoClose: false,
          // closeButton: (data) => CloseButton(data, messageData.link),
        });
        queryClient.invalidateQueries({ queryKey: ['alarm'] });
      }
      // (async () => {
      //   // 브라우저 알림
      //   const showNotification = () => {
      //     const notification = new Notification('코드 봐줘', {
      //       body: data.content,
      //     });

      //     setTimeout(() => {
      //       notification.close();
      //     }, 10 * 1000);

      //     notification.addEventListener('click', () => {
      //       window.open(data.url, '_blank');
      //     });
      //   };

      //   // 브라우저 알림 허용 권한
      //   let granted = false;

      //   if (Notification.permission === 'granted') {
      //     granted = true;
      //   } else if (Notification.permission !== 'denied') {
      //     let permission = await Notification.requestPermission();
      //     granted = permission === 'granted';
      //   }

      //   // 알림 보여주기
      //   if (granted) {
      //     showNotification();
      //   }
      // })();
    });
    eventSource.onmessage = (error) => {
      console.log(error);
    };

    eventSource.onerror = (error) => {
      // console.log(error);
    };
  }, [router]);

  return <></>;
}
