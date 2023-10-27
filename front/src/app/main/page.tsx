import { AiOutlineSearch, AiOutlineSetting } from 'react-icons/ai';
import { IoPersonCircleOutline } from 'react-icons/io5';
import Novel from '../../../public/images/novel.svg';
import Image from 'next/image';
import Link from 'next/link';

export default function MainPage() {
  return (
    <div className="flex">
      <div className="w-56 min-h-screen flex items-end justify-center">
        <div className="flex flex-col gap-4 justify-center items-start font-bold text-lg mb-6">
          <div className="flex items-center gap-2">
            <AiOutlineSearch size={25} />
            <p className="text-xl">이용가이드</p>
          </div>
          <div className="flex items-center gap-2">
            <AiOutlineSetting size={25} />
            <p className="text-xl">설정</p>
          </div>
          <div className="flex items-center gap-2">
            <IoPersonCircleOutline size={25} />
            <p className="text-xl">마이페이지</p>
          </div>
        </div>
      </div>
      <div className="flex justify-center flex-grow">
        <div className="flex flex-col text-2xl font-extrabold gap-14 mt-10">
          <div className="flex flex-col gap-2">
            <div className="font-normal">김채원님 안녕하세요.</div>
            <div>노벨릿에 오신 것을 환영합니다.</div>
          </div>
          <div>
            <div className="text-base mb-2">내 작품</div>
            <div className="border-2 border-dotted grid grid-cols-3 gap-10 p-5">
              {[0, 1, 2, 3, 4, 5].map((el, index) => (
                <Link
                  href="/novel"
                  key={index}
                  className="flex flex-col justify-center items-center gap-2 cursor-pointer"
                >
                  <Image alt="novel" src={Novel} />
                  <div className="text-base">퇴근 후에 만나요</div>
                </Link>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
