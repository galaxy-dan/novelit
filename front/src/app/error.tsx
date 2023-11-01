'use client'; // Error components must be Client Components

import { useEffect } from 'react';
import Image from 'next/image';

export default function Error({
  error,
  reset,
}: {
  error: Error;
  reset: () => void;
}) {
  useEffect(() => {
    console.error(error);
  }, [error]);

  return (
    <div className="overflow-hidden flex items-center justify-center">
      <div className="px-4  items-center flex justify-center flex-col md:gap-28 gap-16">
        <div>
          <Image
            alt="error"
            src="https://i.ibb.co/ck1SGFJ/Group.png"
            width={500}
            height={500}
          />
        </div>
        <div className=" w-full relative pb-12">
          <div className="relative">
            <div className="absolute bottom-0">
              <div>
                <h1 className="my-2 text-gray-800 font-bold text-2xl">
                  {`Looks like you've found the doorway to the great nothing`}
                </h1>
                <p className="my-2 text-gray-800">
                  Sorry about that! Please visit our hompage to get where you
                  need to go.
                </p>
                <button
                  className="w-full my-2 border rounded md py-4 px-8 text-center bg-indigo-600 text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-700 focus:ring-opacity-50"
                  onClick={() => reset()}
                >
                  Go To Main!
                </button>
              </div>
            </div>
            <div>
              <Image
                alt="error"
                src="https://i.ibb.co/G9DC8S0/404-2.png"
                width={700}
                height={700}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
