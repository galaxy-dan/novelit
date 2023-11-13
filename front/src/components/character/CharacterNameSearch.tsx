import { useRouter } from 'next/navigation';
import React, { useState } from 'react';
import { BsSearch } from 'react-icons/bs';
type Props = {
    slug: string
}
export default function CharacterNameSearch({ slug }: Props) {
  const [inputText, setInputText] = useState<string>('');
    const router = useRouter();
  return (
    <div className="flex items-center bg-neutral-200 rounded-md w-fit px-2 py-1 mt-14 text-2xl">
      <label htmlFor="hm">
        <BsSearch />
      </label>
      <input
        id="hm"
        type="text"
        className="bg-neutral-200 rounded-md ml-2 outline-none truncate font-extrabold text-xl"
        placeholder="캐릭터 이름으로 검색..."
        value={inputText}
        onKeyUp={(e) => {
          if (e.key === 'Enter' && inputText.trim().length > 0) {
            router.push(`/character/${slug}/search/${inputText}`);
          }
        }}
        onChange={(e) => {
          setInputText(e.target.value);
        }}
      />
    </div>
  );
}
