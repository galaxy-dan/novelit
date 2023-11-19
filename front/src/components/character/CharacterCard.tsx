import React from 'react';
import Image from 'next/image';
import { characterType } from '@/model/charactor';
import { useRouter } from 'next/navigation';
type Props = {
  character: characterType;
  slug: string;
};
export default function CharacterCard({ character, slug }: Props) {
  const router = useRouter();
  
  return (
    <div
      className="flex border-2 rounded-md w-72 h-40 px-3 items-center shadow-lg mt-8 cursor-pointer hover:bg-neutral-200"
      onClick={() => {
        router.push(
          `/character/${slug}/characterInfo/${character.characterUUID}`,
        );
      }}
    >
      {character.characterImage && character.characterImage !== '' ? (
        <Image
          src={character.characterImage || ''}
          width={100}
          height={100}
          alt="캐릭터이미지"
          className="w-24 h-32 object-cover"
          priority
        />
      ) : (
        <Image
          src="/images/default_character.png"
          width={100}
          height={100}
          alt="캐릭터이미지"
          className="w-24 h-32 object-cover"
          priority
        />
      )}

      <div className="ml-4 w-36 my-auto">
        <p className="font-extrabold text-xl">{character.characterName}</p>
        <div className="font-bold">
          {character?.information &&
            Object.keys(character.information[0]).map((key, i) => (
              <div className="flex mt-1 min-w-0" key={i}>
                <p className="mr-2">{key.split('@;!')[0]}</p>
                <p className="flex-1 truncate">
                  {character?.information && character?.information[0][key]}
                </p>
              </div>
            ))}
        </div>
      </div>
    </div>
  );
}
