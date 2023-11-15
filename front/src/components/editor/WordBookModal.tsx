import { SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import {
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import {
  deleteComment,
  getComment,
  patchEditor,
  postComment,
} from '@/service/api/editor';
import { toast } from 'react-toastify';
import { Comment } from '@/model/editor';
import { Dispatch, SetStateAction } from 'react';
import { AiOutlineMinus } from 'react-icons/ai';
import { RxCross2 } from 'react-icons/rx';
import { deleteWord, getWord, postWord } from '@/service/api/wordbook';
import { Wordbook } from '@/model/wordbook';

const schema = yup
  .object({
    word: yup.string().required().typeError(''),
  })
  .required();

type Inputs = {
  word: string;
};

type Props = {
  workspaceUUID: string;
  setIsOpenWordbook: Dispatch<SetStateAction<boolean>>;
};

export default function WordBookModal({
  workspaceUUID,
  setIsOpenWordbook,
}: Props) {
  const queryClient = useQueryClient();

  const { data: wordList }: UseQueryResult<Wordbook> = useQuery({
    queryKey: ['word', workspaceUUID],
    queryFn: () => getWord({ workspaceUUID }),
    enabled: !!workspaceUUID,
  });

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

  const onSubmit: SubmitHandler<Inputs> = (data) => {
    postMutate.mutate({
      workspaceUUID,
      word: data.word,
    });
  };

  const postMutate = useMutation({
    mutationFn: postWord,
    onSuccess: () => {
      queryClient.invalidateQueries(['word', workspaceUUID]);
    },
  });

  const deleteMutate = useMutation({
    mutationFn: deleteWord,
    onSuccess: () => {
      queryClient.invalidateQueries(['word', workspaceUUID]);
    },
  });
  return (
    <div className="fixed left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 border-2 border-black rounded-md shadow-md p-2 bg-white">
      <div className="flex justify-between">
        <div>Word</div>
        <button onClick={() => setIsOpenWordbook((prev) => !prev)}>
          <AiOutlineMinus />
        </button>
      </div>

      <div className="flex flex-col">
        {wordList?.wordInfo?.map((el, index) => (
          <div className="flex justify-between" key={el.wordUUID}>
            <div className="flex gap-2">
              <div>{el.word}</div>
            </div>
            <button
              onClick={() => {
                deleteMutate.mutate({
                  wordUUID: el.wordUUID,
                });
              }}
            >
              <RxCross2 />
            </button>
          </div>
        ))}
      </div>
      <form onSubmit={handleSubmit(onSubmit)} className="text-sm">
        <input
          type="text"
          {...register('word')}
          className="border-2 m-2 p-1 rounded-lg"
        />
        <button className="border-2 p-2 rounded-full">작성</button>
      </form>
    </div>
  );
}
