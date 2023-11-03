'use client';

import { CSSProperties } from 'react';
import ClimbingBoxLoader from 'react-spinners/ClimbingBoxLoader';

const override: CSSProperties = {
  display: 'block',
  margin: '0 auto',
  borderColor: 'red',
};

export default function Loading() {
  return (
    <div className="flex h-[100vh] justify-center items-center">
      <div>
        <ClimbingBoxLoader size={50} color="#4ade80" cssOverride={override} />
      </div>
    </div>
  );
}
