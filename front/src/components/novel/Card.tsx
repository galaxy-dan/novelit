type Props = {
  subject: string;
  isDirectory: boolean;
};

export default function Card({ subject, isDirectory }: Props) {
  return (
    <div
      className={`flex items-end w-52 h-32 rounded-lg border-2 border-gray-200 ${
        isDirectory ? 'bg-yellow-200' : 'bg-gray-200'
      }`}
    >
      <div className="w-full h-1/2 bg-white rounded-b-lg p-2 overflow-ellipsis whitespace-nowrap overflow-hidden break-all">
        {subject}
      </div>
    </div>
  );
}
