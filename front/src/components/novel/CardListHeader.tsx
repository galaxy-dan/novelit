import { useState } from 'react';
import CreateButton from '../CreateButton';
import NewDirectoryModal from './NewDirectoryModal';

type Props = {
  title: string;
};

type Modal = {
  isOpen: boolean;
  isDirectory: boolean;
};

export default function CardListHeader({ title }: Props) {
  const [modal, setModal] = useState<Modal>({
    isOpen: false,
    isDirectory: false,
  });

  const buttonClick = (directory: boolean) => {
    setModal((prev) => ({
      ...prev,
      isOpen: !prev.isOpen,
      isDirectory: directory,
    }));
  };

  return (
    <>
      <div className="flex justify-between my-32">
        <div className="font-extrabold text-4xl">{title}</div>

        <div className="flex">
          <CreateButton onClick={() => buttonClick(false)} content="파일" />
          <CreateButton onClick={() => buttonClick(true)} content="폴더" />
        </div>
      </div>
      {modal.isOpen && (
        <NewDirectoryModal setModal={setModal} isDirectory={modal.isDirectory} />
      )}
    </>
  );
}
