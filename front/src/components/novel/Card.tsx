type Props = {
  subject: string;
};

export default function Card({ subject }: Props) {
  return (
    <div className="flex items-end w-52 h-32 bg-gray-200 rounded-lg border-2 border-gray-200">
      <div className="w-full h-1/2 bg-white rounded-b-lg p-2">{subject}</div>
    </div>
  );
}
