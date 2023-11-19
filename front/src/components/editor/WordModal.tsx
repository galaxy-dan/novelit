import { Word } from '@/model/editor';
import hanspell from 'hanspell';
import { Dispatch, RefObject, SetStateAction, useState } from 'react';
import { RxCross2 } from 'react-icons/rx';
import { MdOutlineAutorenew } from 'react-icons/md';
import { wordCheck } from '@/service/editor/editor';

type Props = {
  wordList: Word[];
  setIsOpenWord: Dispatch<SetStateAction<boolean>>;
  setWordList: Dispatch<SetStateAction<Word[]>>;
  word: string;
  workspaceUUID: string;
};

export default function WordModal({
  wordList,
  setIsOpenWord,
  word,
  workspaceUUID,
  setWordList,
}: Props) {
  return (
    <div className="w-56 h-screen overflow-auto scrollbar-hide fixed right-2 top-0 flex flex-col gap-8 border-2 p-2 bg-white">
      <div className="flex items-center justify-between">
        <div className="font-bold text-2xl">맞춤법 검사 결과</div>
        <div className='flex items-center'>
          <button
            onClick={() =>
              wordCheck({
                word,
                workspaceUUID,
              }).then((data) => {
                setWordList(data);
              })
            }
          >
            <MdOutlineAutorenew size={20} />
          </button>
          <button onClick={() => setIsOpenWord(false)}>
            <RxCross2 size={20} />
          </button>
        </div>
      </div>
      {wordList.length == 0 ? (
        <div className="font-bold">검사결과가 없습니다!</div>
      ) : (
        wordList.map((el, index) => (
          <div key={index}>
            <div>
              <span className="font-bold">입력내용&nbsp;</span>
              {`- ${el.token}`}
            </div>
            <div>
              <span className="font-bold">대치어&nbsp;</span>
              {`- [${el.suggestions.join(', ')}]`}
            </div>
            <div>
              <span className="font-bold">도움말&nbsp;</span>
              {`- ${el.info}`}
            </div>
          </div>
        ))
      )}
    </div>
  );
}
