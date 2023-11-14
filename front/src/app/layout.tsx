import QueryProvider from '@/context/QueryProvider';
import './globals.css';
import type { Metadata } from 'next';
import localFont from 'next/font/local';
import { Nanum_Myeongjo } from 'next/font/google';
import RecoilProvider from '@/context/RecoilProvider';
import SideMenu from '@/components/SideMenu';
import { Hi_Melody } from 'next/font/google';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Favicon from '../../public/vercel.svg';
import SSE from '@/components/SSE';
import NotificationButton from '@/components/NotificationButton';
import Side from '@/components/Side';

// const nanumFont = Nanum_Myeongjo({
//   subsets: ['latin'],
//   weight: ['400', '700', '800'],
// });
const nanumFont = Hi_Melody({
  subsets: ['latin'],
  weight: ['400'],
});

export const metadata: Metadata = {
  title: 'Novelit',
  description: '하나뿐인 소설 에디터',
  icons: {
    icon: '/images/icon.svg',
  },
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko" className={nanumFont.className}>
      <body className="w-full flex relative">
        <QueryProvider>
          <RecoilProvider>
            <SSE />
            <ToastContainer pauseOnFocusLoss={false} />
            <NotificationButton />
            <Side />
            <main className="flex-grow">{children}</main>
          </RecoilProvider>
        </QueryProvider>
      </body>
    </html>
  );
}
