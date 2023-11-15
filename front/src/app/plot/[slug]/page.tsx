'use client';
import PlotCard from '@/components/plot/PlotCard';
import { plotListType } from '@/model/plot';
import { getPlotList, postPlot } from '@/service/api/plot';
import {
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import React, { useEffect, useState } from 'react';
import { BsSearch } from 'react-icons/bs';
import { GoBook } from 'react-icons/go';
import { LuBookUp2 } from 'react-icons/lu';
type Props = {
  params: {
    slug: string;
  };
};
export default function page({ params }: Props) {
  const queryClient = useQueryClient();

  const [title, setTitle] = useState<string>('');
  const [input, setInput] = useState<string>('');

  const { data: plotInfoDtoList }: UseQueryResult<plotListType> = useQuery({
    queryKey: ['plotList', title],
    queryFn: () => getPlotList(params.slug, title),
  });

  const createMutate = useMutation({
    mutationFn: () =>
      postPlot({ workspaceUuid: params.slug, plotTitle: '새 플롯' }),
    onSuccess: () => {
      queryClient.refetchQueries(['plotDirectory']);
      queryClient.refetchQueries(['plotList']);
    },
  });

  useEffect(() => {
    const debounce = setTimeout(() => {
      return setTitle(input);
    }, 800);
    return () => {
      clearTimeout(debounce);
    };
  }, [input]);

  return (
    <div className="ml-14 my-20 select-none">
      {/* 제목 */}
      <div className="flex items-end text-4xl">
        <GoBook className="mr-2" />
        <p className="text-3xl font-extrabold">플롯 홈</p>
      </div>
      {/* 검색 창 */}
      <div className="w-full flex justify-between">
        <div className="flex items-center bg-neutral-200 rounded-md w-fit px-2 py-1 mt-11 text-2xl">
          <label htmlFor="hm">
            <BsSearch />
          </label>
          <input
            id="hm"
            type="text"
            className="bg-neutral-200 rounded-md ml-2 outline-none truncate font-extrabold text-xl"
            value={input}
            onChange={(e) => {
              setInput(e.target.value);
            }}
            placeholder="이름으로 검색"
          />
        </div>
        <div className="flex mt-11 mr-[7rem]">
          <div
            onClick={() => {
              createMutate.mutate();
            }}
            className="flex items-center bg-neutral-200 hover:bg-neutral-400 rounded-md w-fit px-2 py-1 text-2xl cursor-pointer font-bold mr-2"
          >
            <LuBookUp2 className="mr-2" />새 플롯
          </div>
        </div>
      </div>
      <div className=" grid gap-4 a2:grid-cols-1 b2:grid-cols-1 c2:grid-cols-2 d2:grid-cols-3 e2:grid-cols-4 f2:grid-cols-5 ">
        {plotInfoDtoList?.plotInfoDtoList?.map((plot) => (
          <PlotCard plot={plot} workspace={params.slug} key={plot.plotUuid} />
        ))}
      </div>
    </div>
  );
}
