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

const schema = yup
  .object({
    commentContent: yup.string().required().typeError(''),
  })
  .required();

type Inputs = {
  commentContent: string;
};

type Props = {
  spaceUUID: string;
  directoryUUID: string | string[];
  setIsOpen: Dispatch<SetStateAction<boolean>>;
};

export default function Comment({
  spaceUUID,
  directoryUUID,
  setIsOpen,
}: Props) {
  const queryClient = useQueryClient();

  const { data: commentList }: UseQueryResult<Comment[]> = useQuery({
    queryKey: ['comment', spaceUUID],
    queryFn: () => getComment({ spaceUUID }),
    enabled: !!spaceUUID,
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
      spaceUUID,
      directoryUUID,
      commentContent: data.commentContent,
      commentNickname: 'john',
      commentPassword: 'bye',
    });
  };

  const patchMutate = useMutation({
    mutationFn: patchEditor,
    onSuccess: () => {
      queryClient.invalidateQueries(['editor']);
      toast('저장 성공');
    },
  });

  const postMutate = useMutation({
    mutationFn: postComment,
    onSuccess: () => {
      toast('댓글 작성 성공');
      queryClient.invalidateQueries(['comment', spaceUUID]);

      // 글도 최신화
      patchMutate.mutate({
        uuid: directoryUUID,
        content:
          document.getElementById('edit')?.innerHTML ?? '<div><br/></div>',
      });
    },
  });

  const deleteMutate = useMutation({
    mutationFn: deleteComment,
    onSuccess: () => {
      queryClient.invalidateQueries(['comment', spaceUUID]);
    },
  });
  return (
    <div className="fixed left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 border-2 border-black rounded-md shadow-md p-2">
      <div className="flex justify-between">
        <div>Comment</div>
        <button onClick={() => setIsOpen((prev) => !prev)}>
          <AiOutlineMinus />
        </button>
      </div>

      <div className="flex">
        {commentList?.map((el, index) => (
          <div className="flex justify-between" key={el.commentUUID}>
            <div className="flex gap-2">
              <div>{el.commentNickname}</div>
              <div>{el.commentContent}</div>
            </div>
            <button
              onClick={() => {
                deleteMutate.mutate({
                  spaceUUID,
                  commentUUID: el.commentUUID,
                  commentNickname: el.commentNickname,
                  commentPassword: el.commentPassword,
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
          {...register('commentContent')}
          className="border-2 m-2 p-1 rounded-lg"
        />
        <button className="border-2 p-2 rounded-full">작성</button>
      </form>
    </div>
  );
}
