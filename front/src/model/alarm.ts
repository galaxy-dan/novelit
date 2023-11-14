export type Alarm = {
  type: number;
  senderId: string;
  senderName: string;
  context: string;
  createdAt: Date;
  nickname: string;
  produceName: string;
};

export type Message = {
  pubName: string;
  directoryName: string;
};
