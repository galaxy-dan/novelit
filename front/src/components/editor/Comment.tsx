import { SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { useMutation } from '@tanstack/react-query';
import { postComment } from '@/service/api/editor';
import { toast } from 'react-toastify';

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
  directoryUUID: string;
};

export default function Comment({ spaceUUID, directoryUUID }: Props) {
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

  const postMutate = useMutation({
    mutationFn: postComment,
    onSuccess: () => {
      toast('댓글 작성 성공');
    },
  });
  return (
    <>
      <div>Comment</div>
      <div>글 내용</div>
      <div>댓글 닉</div>
      <div>댓글 내용</div>
      <form onSubmit={handleSubmit(onSubmit)} className='border-2 border-black px-2 py-1 shadow-md rounded-md'>
        <input type="text" {...register('commentContent')} />
        <button className='border-2 p-2 rounded-full'>작성</button>
      </form>
    </>
  );
}
