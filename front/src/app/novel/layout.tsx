import SideMenu from '@/components/SideMenu';

export default function NovelLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="flex">
      <SideMenu />
      <div>{children}</div>
    </div>
  );
}
