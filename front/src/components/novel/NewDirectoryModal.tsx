'use client';
import { MdOutlineCancel } from 'react-icons/md';
import { yupResolver } from '@hookform/resolvers/yup';
import { useForm, SubmitHandler, UseFormReturn } from 'react-hook-form';
import * as yup from 'yup';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { patchWorkspace, postWorkspace } from '@/service/api/workspace';
import { Dispatch, SetStateAction } from 'react';
import { useParams } from 'next/navigation';
import { patchDirectory, postDirectory } from '@/service/api/novel';
import { v4 as uuidv4 } from 'uuid';

const schema = yup
  .object({
    name: yup.string().required().typeError(''),
  })
  .required();

type Inputs = {
  name: string;
};

type Modal = {
  isOpen: boolean;
  isDirectory: boolean;
};

type Props = {
  setModal: Dispatch<SetStateAction<Modal>>;
  isDirectory: boolean;
};

export default function NewDirectoryModal({ setModal, isDirectory }: Props) {
  const queryClient = useQueryClient();
  const {
    register,
    handleSubmit,
    watch,
    setValue,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<Inputs>({
    resolver: yupResolver(schema),
  });

  const searchParams = useParams();

  const slug = Array.isArray(searchParams.slug)
    ? searchParams.slug[0]
    : searchParams.slug;

  const postMutate = useMutation({
    mutationFn: postDirectory,
    onSuccess: () => {
      queryClient.invalidateQueries(['workspace', slug]);
      setModal((prev) => ({ ...prev, isOpen: !prev.isOpen }));
    },
  });

  const onSubmit: SubmitHandler<Inputs> = (data) => {
    const uuid = uuidv4();
    postMutate.mutate({
      name: watch('name'),
      workspaceUUID: slug,
      directory: isDirectory,
      parentUUID: slug,
      uuid,
    });
  };
  return (
    <div className="flex flex-col gap-4 absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 bg-white p-6 pt-2 pr-2 border-2 border-black rounded-xl">
      <div
        className="flex justify-end cursor-pointer"
        onClick={() => {
          setModal((prev) => ({ ...prev, isOpen: !prev.isOpen }));
        }}
      >
        <MdOutlineCancel />
      </div>
      <div>
        <div className="text-center mb-2 font-bold text-lg">{`${
          isDirectory ? '새 폴더' : '새 파일'
        }`}</div>
        <div>
          <form onSubmit={handleSubmit(onSubmit)}>
            <input
              type="text"
              placeholder={`${isDirectory ? '폴더' : '파일'}`}
              className="border-2 px-1 rounded-md"
              {...register('name')}
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
