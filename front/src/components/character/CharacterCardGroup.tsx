import { characterType, subGroupType } from '@/model/charactor';
import React from 'react';
import SubGroupCard from './SubGroupCard';
import CharacterCard from './CharacterCard';
type Props = {
  characters: characterType[];
  slug: string;
};
export default function CharacterCardGroup({ characters, slug }: Props) {
  return (
    <div className="grid a:grid-cols-1 b:grid-cols-2 c:grid-cols-3 d:grid-cols-4 e:grid-cols-5 f:grid-cols-6 grid-flow-row gap-4 ">
      {characters?.map((character, i) => (
        !character.deleted &&
        <CharacterCard
          character={character}
          slug={slug}
          key={character.characterUUID}
        />
      ))}
    </div>
  );
}
