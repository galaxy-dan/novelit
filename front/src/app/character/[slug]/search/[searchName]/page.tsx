'use client';
import CharacterCardGroup from '@/components/character/CharacterCardGroup';
import CharacterHomeTitle from '@/components/character/CharacterHomeTitle';
import { characterType } from '@/model/charactor';
import { getCharacterByName } from '@/service/api/character';
import { UseQueryResult, useQuery } from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react';
import { BsSearch } from 'react-icons/bs';
type Props = {
  params: {
    slug: string;
    searchName: string;
  };
};

export default function page({ params }: Props) {
  const router = useRouter();
  const [inputText, setInputText] = useState<string>(
    decodeURIComponent(params.searchName),
  );
  const [inputName, setInputName] = useState<string>(
    decodeURIComponent(params.searchName),
  );
  const { data: searchCharacterData }: UseQueryResult<characterType[]> =
    useQuery({
      queryKey: ['group', inputName],
      queryFn: () => getCharacterByName(params.slug, inputName),
      onError: () => {
        router.push(`/main`);
      },
    });

  useEffect(() => {
    const debounce = setTimeout(() => {
      return setInputName(inputText);
    }, 500);
    return () => {
      clearTimeout(debounce);
    };
  }, [inputText]);

  return (
    <div className="ml-10 py-20 select-none  h-screen overflow-y-scroll scrollbar-hide">
      {/* 제목 */}
      <CharacterHomeTitle slug={params.slug} />
      {/* 검색 창 */}
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
          onChange={(e) => {
            setInputText(e.target.value);
          }}
        />
      </div>
      {/* 캐릭터 카드 전체 모음 */}
      <div className="mt-6">
        {/* 캐릭터 카드 그룹 */}
        <div>
          <p className="text-4xl font-extrabold max-w-[30rem] truncate">
            캐릭터 이름으로 검색
          </p>
        </div>

        <div className="grid a:grid-cols-1 b:grid-cols-2 c:grid-cols-3 d:grid-cols-4 e:grid-cols-5 f:grid-cols-6 grid-flow-row gap-4 ">
          <CharacterCardGroup
            slug={params.slug}
            characters={searchCharacterData || []}
          />
          {searchCharacterData?.length === 0 && (
            <div>검색 결과가 없습니다...</div>
          )}
        </div>
      </div>
    </div>
  );
}
