export type PostDirectory = {
  name: string;
  workspaceUUID: string;
  parentUUID: string | null;
  directory: boolean;
};

export type PatchDirectory = {
  uuid: string;
  name: string;
};
