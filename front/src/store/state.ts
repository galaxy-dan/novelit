import { atom } from 'recoil';

export const isMovableState = atom<boolean>({
  key: 'isMovable',
  default: true,
});
