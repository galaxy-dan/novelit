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

// const nanumFont = Nanum_Myeongjo({
//   subsets: ['latin'],
//   weight: ['400', '700', '800'],
// });
const nanumFont = Hi_Melody({
  subsets: ['latin'],
  weight: ['400'],
});

export const metadata: Metadata = {
  title: 'Create Next App',
  description: 'Generated by create next app',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko" className={nanumFont.className}>
      <body className="w-full flex">
        <QueryProvider>
          <RecoilProvider>
            <ToastContainer pauseOnFocusLoss={false} />
            <SideMenu />
            <main className='flex-grow'>{children}</main>
          </RecoilProvider>
        </QueryProvider>
      </body>
    </html>
  );
}
