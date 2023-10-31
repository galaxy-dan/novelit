'use client';
import { MdOutlineCancel } from 'react-icons/md';
import { yupResolver } from '@hookform/resolvers/yup';
import { useForm, SubmitHandler, UseFormReturn } from 'react-hook-form';
import * as yup from 'yup';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { patchWorkspace, postWorkspace } from '@/service/api/workspace';
import { Dispatch, SetStateAction } from 'react';

type Inputs = {
  workspaceUUID?: string | undefined;
  title: string;
};

type Props = {
  setIsOpen: Dispatch<SetStateAction<boolean>>;
  form: UseFormReturn<Inputs, any, undefined>;
};

export default function NewWorkspaceModal({ setIsOpen, form }: Props) {
  const queryClient = useQueryClient();
  const {
    register,
    handleSubmit,
    watch,
    setValue,
    reset,
    formState: { errors, isSubmitting },
  } = form;

  const postMutate = useMutation({
    mutationFn: postWorkspace,
    onSuccess: () => {
      setIsOpen((prev) => !prev);
      queryClient.invalidateQueries(['user']);
      console.log('성공');
    },
  });

  const patchMutate = useMutation({
    mutationFn: patchWorkspace,
    onSuccess: () => {
      setIsOpen((prev) => !prev);
      queryClient.invalidateQueries(['user']);
      reset();
    },
  });

  const onSubmit: SubmitHandler<Inputs> = (data) => {
    console.log(data.workspaceUUID);
    if (data.workspaceUUID && data.workspaceUUID.length > 0) {
      patchMutate.mutate(data);
    } else {
      postMutate.mutate({ title: data.title });
    }
  };
  return (
    <div className="flex flex-col gap-4 absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 bg-white p-6 pt-2 pr-2 border-2 border-black rounded-xl">
      <div
        className="flex justify-end cursor-pointer"
        onClick={() => {
          setIsOpen((prev) => !prev);
        }}
      >
        <MdOutlineCancel />
      </div>
      <div>
        <div className="text-center mb-2 font-bold text-lg">새 작품</div>
        <div>
          <form onSubmit={handleSubmit(onSubmit)}>
            <input type="hidden" {...register('workspaceUUID')} />
            <input
              type="text"
              placeholder="작품명"
              className="border-2 px-1 rounded-md"
              {...register('title')}
            />
            <button className="ml-2 border-2 border-red-100 p-1 rounded-full text-sm">
              생성
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
