export type PostDirectory = {
  name: string;
  workspaceUUID: string | string[];
  parentUUID: string | null | undefined;
  directory: boolean;
};

export type PatchDirectory = {
  uuid: string;
  name: string;
};
