import Link from 'next/link';
import { IoExtensionPuzzle, IoExtensionPuzzleOutline } from 'react-icons/io5';
import { MdOutlineStickyNote2, MdStickyNote2 } from 'react-icons/md';
import Image from 'next/image';
import People from '../../public/images/people.svg';
import PeopleWhite from '../../public/images/peopleWhite.svg';
import { usePathname } from 'next/navigation';

type Props = {
  slug: string;
};

export default function SideMenuMoveButton({ slug }: Props) {
  const path = usePathname();
  const pathname = path.split('/')[1];

  return (
    <div className="flex flex-col gap-4 border-r-2 border-gray-300 p-2">
      <Link href={`/novel/${slug}`}>
        {pathname === 'novel' || pathname === 'editor' ? (
          <MdStickyNote2 size={20} />
        ) : (
          <MdOutlineStickyNote2 size={20} />
        )}
      </Link>
      <Link href={`/plot/${slug}`}>
        {pathname === 'plot' ? (
          <IoExtensionPuzzle size={20} />
        ) : (
          <IoExtensionPuzzleOutline size={20} />
        )}
      </Link>
      <Link href={`/character/${slug}`}>
        {pathname === 'character' ? (
          <Image alt="people" src={People} width={20} />
        ) : (
          <Image alt="people" src={PeopleWhite} width={20} />
        )}
      </Link>
    </div>
  );
}
