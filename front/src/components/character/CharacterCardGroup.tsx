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
    <div className="flex flex-wrap gap-4 ">
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
