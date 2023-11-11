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
      toast(event.data);
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