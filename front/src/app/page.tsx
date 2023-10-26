import Image from 'next/image';
import KakaoButton from '../../public/images/kakao_login_large_wide.svg';
import Logo from '../../public/images/NOVELIT.svg';
import { AiOutlineSearch } from 'react-icons/ai';
import Link from 'next/link';

export default function Home() {
  return (
    <>
      <div className="min-h-screen flex flex-col justify-center items-center gap-44 bg-[url('../../public/images/olia-gozha-J4kK8b9Fgj8-unsplash.jpg')] bg-cover">
        <div className="flex flex-col font-extrabold text-5xl gap-6 items-center">
          <Image alt="logo" src={Logo} width={500} />
        </div>
        <Link href={'/editor'}>
          <Image alt="kakao" src={KakaoButton} width={400} />
        </Link>
      </div>
      <div className="flex gap-2 justify-center items-center fixed bottom-24 left-16 font-bold text-lg">
        <AiOutlineSearch />
        <p className="text-xl">이용가이드</p>
      </div>
    </>
  );
}
