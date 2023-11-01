export type Editor = {
  title: string;
  content: string;
};

export type PatchEditor = {
  uuid: string | string[];
  content: string;
};

export type Reply = {
  id: string;
  detail: string;
};
