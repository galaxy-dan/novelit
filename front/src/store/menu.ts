import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();
export const menuOpenState = atom<boolean>({
  key: 'menu',
  default: false,
  effects_UNSTABLE: [persistAtom],
});
