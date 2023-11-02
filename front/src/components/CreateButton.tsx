import { MouseEventHandler } from 'react';
import { BiPencil } from 'react-icons/bi';

type Props = {
  onClick: MouseEventHandler<HTMLButtonElement>;
  content: string;
};

export default function CreateButton({ onClick, content }: Props) {
  return (
    <button
      className="flex items-center justify-center text-sm border-2 rounded-md p-1 mr-5"
      onClick={onClick}
    >
      <BiPencil />
      <div>{content}</div>
    </button>
  );
}
