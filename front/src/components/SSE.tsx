'use client';

import { getConfig, post } from '@/service/api/http';
import { useContext, useEffect, useState } from 'react';
import { EventSourcePolyfill, NativeEventSource } from 'event-source-polyfill';
import { toast } from 'react-toastify';
import { getAlarmMessage } from '@/service/alarm';
import { Alarm } from '@/model/alarm';
import { useRouter } from 'next/navigation';
import { useQueryClient } from '@tanstack/react-query';
import { AuthContext } from '@/context/AuthContext';

export default function SSE() {
  const [listening, setListening] = useState(false);
  const [data, setData] = useState({ value: 0, target: 100 });
  const router = useRouter();
  const queryClient = useQueryClient();

  const { user } = useContext(AuthContext);

  
  
  useEffect(() => {
    if (!user) return;
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
      const data = JSON.parse(event.data);
      if (data.type !== 'Connection') {
        queryClient.refetchQueries(['editor']);
        queryClient.refetchQueries(['alarm']);
        toast(data.content);

      }
    });
    eventSource.onmessage = (error) => {
      console.log(error);
    };

    eventSource.onerror = (error) => {
      // console.log(error);
    };
  }, [router, user]);

  return <></>;
}
