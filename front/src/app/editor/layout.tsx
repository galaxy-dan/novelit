import SideMenu from '@/components/SideMenu';

export default function EditorLayout({
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
