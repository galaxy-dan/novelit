import Card from '@/components/novel/Card';
import Link from 'next/link';

export default function NovelPage() {
  return (
    <div className="pl-48 font-melody">
      <div className="font-extrabold text-4xl my-32">퇴근후에 만나요</div>
      <div className="flex flex-col gap-20">
        {[0, 1, 2].map((el, i) => (
          <div key={i} className="flex flex-col gap-4">
            <Card subject={`${i + 1}권`} />
            <div className="flex flex-wrap gap-6">
              {[0, 1, 2, 3, 4].map((elem, index) => (
                <Link key={index} href="/editor">
                  <Card subject={`${index + 1}화`} />
                </Link>
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
