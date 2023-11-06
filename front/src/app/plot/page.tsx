import PlotCard from '@/components/plot/PlotCard';
import { plotType } from '@/model/plot';
import { getPlotList } from '@/service/api/plot';
import { UseQueryResult, useQuery } from '@tanstack/react-query';
import React from 'react';
import { BsSearch } from 'react-icons/bs';
import { GoBook } from 'react-icons/go';

export default function page() {
  const { data: plotList }: UseQueryResult<plotType[]> = useQuery({
    queryKey: ['plotList'],
    queryFn: () => getPlotList("","")
  });

  return (
    <div className="ml-32 my-20 w-[60vw] min-w-[50rem] max-w-[100rem]  select-none">
      {/* 제목 */}
      <div className="flex items-end text-4xl">
        <GoBook className="mr-2" />
        <p className="text-3xl font-extrabold">플롯 홈</p>
      </div>
      {/* 검색 창 */}
      <div className="flex items-center bg-neutral-200 rounded-md w-fit px-2 py-1 mt-14 text-2xl">
        <label htmlFor="hm">
          <BsSearch />
        </label>
        <input
          id="hm"
          type="text"
          className="bg-neutral-200 rounded-md ml-2 outline-none truncate font-extrabold text-xl"
        />
      </div>
      <div className="grid b2:grid-cols-1 c2:grid-cols-2 d2:grid-cols-3 e2:grid-cols-4 f2:grid-cols-5 grid-flow-row gap-4 ">
        <PlotCard />
        <PlotCard />
        <PlotCard />
        <PlotCard />
        <PlotCard />
        <PlotCard />
        <PlotCard />
        <PlotCard />
        <PlotCard />
        <PlotCard />
      </div>
    </div>
  );
}
