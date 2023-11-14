import { plotType } from '@/model/plot';
import { useRouter } from 'next/navigation';
import React from 'react';
import { BsJournalBookmark } from 'react-icons/bs';
type Props = {
  plot: plotType;
  workspace : string;
};
export default function PlotCard({ plot , workspace}: Props) {
  const router = useRouter();
  return (
    <div className="flex border-2 rounded-md w-[24rem] h-40 px-3 items-center shadow-lg mt-8 cursor-pointer"
      onClick={()=>{
        console.log(`/plot/${workspace}/${plot.plotUuid}`);
        router.push(`/plot/${workspace}/${plot.plotUuid}`);
      }}
    >
      <BsJournalBookmark className="text-[5rem]" />
      <div className="ml-4 w-72 my-auto">
        <p className="font-extrabold text-2xl">{plot.plotTitle}</p>
        <div className="font-bold ">
          <div className="flex mt-1 min-w-0">
            <p className="flex-1 line-clamp-2 text-lg">
              {plot.story}
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
