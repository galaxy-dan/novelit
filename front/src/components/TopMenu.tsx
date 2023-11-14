'use client';

import { usePathname } from 'next/navigation';
import NotificationButton from './NotificationButton';
import Logout from './login/Logout';

export default function TopMenu() {
  const pathname = usePathname();
  if (pathname === '/' || pathname.startsWith('/login')) return;
  return (
    <div className="fixed top-5 right-5 flex items-center">
      <Logout />
      <NotificationButton />
    </div>
  );
}
