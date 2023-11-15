export type PostWordbook = {
  workspaceUUID: string;
  word: string;
};

export type WordInfo = { wordUUID: string; word: string; character: boolean };

export type Wordbook = {
  workspaceUUID: string;
  wordInfo: WordInfo[];
};
