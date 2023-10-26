'use client';

import { RecoilRoot } from 'recoil';

type Props = {
  children: React.ReactNode;
};

export default function RecoilProvider({ children }: Props) {
  return <RecoilRoot>{children}</RecoilRoot>;
}
