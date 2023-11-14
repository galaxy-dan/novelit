import { atom } from 'recoil';

export const menuOpenState = atom<boolean>({
  key: 'menu',
  default: false,
});
