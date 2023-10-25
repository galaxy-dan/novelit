'use client';

import {
  QueryCache,
  QueryClient,
  QueryClientProvider,
} from '@tanstack/react-query';
import { useState } from 'react';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { toast } from 'react-toastify';
import { redirect, useRouter } from 'next/navigation';
import { AxiosError } from 'axios';

type Props = {
  children: React.ReactNode;
};



export default function QueryProvider({ children }: Props) {
  const router = useRouter();
  const [queryClient] = useState(
    () =>
      new QueryClient({
        defaultOptions: {
          queries: {
            // suspense: true,
            staleTime: 1000 * 20,
            retry: false
          },
        },
        queryCache: new QueryCache({
          onError: (error, query) => {
            if (error instanceof AxiosError) {
              if (error?.response?.data.httpStatus === 'UNAUTHORIZED') {
                toast.error(error?.response?.data.message);
                localStorage.removeItem('accessToken');
                router.push('/login');
              }
            }
          },
        }),
      }),
  );
  return (
    <QueryClientProvider client={queryClient}>
      {children}
      <ReactQueryDevtools initialIsOpen={true} />
    </QueryClientProvider>
  );
}
