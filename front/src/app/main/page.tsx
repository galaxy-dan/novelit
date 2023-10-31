'use client';

import { AiOutlineSearch, AiOutlineSetting } from 'react-icons/ai';
import { IoPersonCircleOutline } from 'react-icons/io5';
import { BiPencil } from 'react-icons/bi';
import Novel from '../../../public/images/novel.svg';
import Image from 'next/image';
import Link from 'next/link';
import {
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { User, Workspace } from '@/model/workspace';
import {
  deleteWorkspace,
  getUser,
  postWorkspace,
} from '@/service/api/workspace';
import { useState } from 'react';
import NewWorkspaceModal from '@/components/workspace/NewWorkspaceModal';
import { MdEdit } from 'react-icons/md';
import { RxCross2 } from 'react-icons/rx';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { toast } from 'react-toastify';

const schema = yup
  .object({
    workspaceUUID: yup.string(),
    title: yup.string().required().typeError(''),
  })
  .required();

type Inputs = {
  workspaceUUID?: string | undefined;
  title: string;
};

export default function MainPage() {
  const queryClient = useQueryClient();
  const [isOpen, setIsOpen] = useState<boolean>(false);
  // const [workspaceUUID]

  const { data: user }: UseQueryResult<User> = useQuery({
    queryKey: ['user'],
    queryFn: getUser,
    // enabled: !!user?.userId,
  });

  const delMutate = useMutation({
    mutationFn: deleteWorkspace,
    onSuccess: () => {
      console.log('삭제');
      toast('삭제 성공');
      queryClient.invalidateQueries(['user']);
    },
  });

  const form = useForm<Inputs>({
    resolver: yupResolver(schema),
  });

  return (
    <>
      <div className="flex">
        <div className="w-1/2 min-h-screen flex items-end justify-center">
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
            <div className="flex justify-between items-center">
              <div className="flex flex-col gap-2">
                <div className="font-normal">김채원님 안녕하세요.</div>
                <div>노벨릿에 오신 것을 환영합니다.</div>
              </div>

              <button
                className="flex items-center justify-center text-sm border-2 rounded-md p-1 mr-5"
                onClick={() => {
                  setIsOpen((prev) => !prev);
                }}
              >
                <BiPencil />
                <div>새 작품</div>
              </button>
            </div>
            <div>
              <div className="text-base mb-2">내 작품</div>
              <div className="border-2 border-dotted flex flex-wrap gap-10 p-5 justify-center mr-10">
                {user?.workspaces?.map((el, index) => (
                  <Link
                    href={`/novel/${el.workspaceUUID}`}
                    key={el.workspaceUUID}
                    className="flex flex-col relative justify-center items-center gap-2 cursor-pointer hover:bg-gray-200 hover:bg-opacity-30 hover:rounded-md text-white hover:text-black"
                  >
                    <Image alt="novel" src={Novel} />
                    <div className="text-base text-black">{el.title}</div>
                    <div className="absolute top-0 right-0 flex items-center text-base m-1">
                      <button
                        onClick={(e) => {
                          e.preventDefault();
                          form.setValue('workspaceUUID', el.workspaceUUID);
                          form.setValue('title', el.title);
                          setIsOpen((prev) => !prev);
                        }}
                        title="Rename..."
                      >
                        <MdEdit />
                      </button>
                      <button
                        onClick={(e) => {
                          e.preventDefault();
                          delMutate.mutate(el.workspaceUUID);
                        }}
                        title="Delete"
                      >
                        <RxCross2 />
                      </button>
                    </div>
                  </Link>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
      {isOpen && <NewWorkspaceModal setIsOpen={setIsOpen} form={form} />}
    </>
  );
}
