import Link from 'next/link';
import { IoExtensionPuzzle, IoExtensionPuzzleOutline } from 'react-icons/io5';
import { MdOutlineStickyNote2, MdStickyNote2 } from 'react-icons/md';
import Image from 'next/image';
import People from '../../public/images/people.svg';
import PeopleWhite from '../../public/images/peopleWhite.svg';
import { usePathname, useRouter } from 'next/navigation';
import { useRecoilState } from 'recoil';
import { isMovableState } from '@/store/state';

type Props = {
  slug: string;
};

export default function SideMenuMoveButton({ slug }: Props) {
  const path = usePathname();
  const pathname = path.split('/')[1];
  const router = useRouter();
  const [isMovable, setIsMovable] = useRecoilState<boolean>(isMovableState);
  return (
    <div className="flex flex-col gap-4 border-r-2 border-gray-300 p-2">
      <div
        className="cursor-pointer"
        onClick={() => {
          if (isMovable || confirm('기록이 저장되지 않을 수 있습니다.')) {
            router.push(`/novel/${slug}`);
          }
        }}
      >
        {pathname === 'novel' || pathname === 'editor' ? (
          <MdStickyNote2 size={20} />
        ) : (
          <MdOutlineStickyNote2 size={20} />
        )}
      </div>
      <div
        className="cursor-pointer"
        onClick={() => {
          if (isMovable || confirm('기록이 저장되지 않을 수 있습니다.')) {
            router.push(`/plot/${slug}`);
          }
        }}
      >
        {pathname === 'plot' ? (
          <IoExtensionPuzzle size={20} />
        ) : (
          <IoExtensionPuzzleOutline size={20} />
        )}
      </div>
      <div
        className="cursor-pointer"
        onClick={() => {
          if (isMovable || confirm('기록이 저장되지 않을 수 있습니다.')) {
            router.push(`/character/${slug}`);
          }
        }}
      >
        {pathname === 'character' ? (
          <Image alt="people" src={People} width={20} />
        ) : (
          <Image alt="people" src={PeopleWhite} width={20} />
        )}
      </div>
    </div>
  );
}
