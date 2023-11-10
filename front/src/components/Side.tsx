'use client'

import { usePathname } from 'next/navigation';
import React from 'react';
import SideMenu from './SideMenu';
import SideMenuPlot from './SideMenuPlot';

export default function Side() {
  const path = usePathname();
  const pathname = path.split('/')[1];

  return (
    <>
      {pathname === 'novel' && <SideMenu />}
      {pathname === 'plot' && <SideMenuPlot/>}
      {pathname === 'character' && <SideMenuPlot/>}
    </>
  );
}
