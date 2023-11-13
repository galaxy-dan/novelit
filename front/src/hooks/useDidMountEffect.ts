import { useEffect, useRef } from 'react';

export const useDidMountEffect = (func: any, deps: any) => {
  const didMount = useRef(0);
  
  useEffect(() => {
    if (didMount.current > 1) func();
    else didMount.current++;
  }, deps);
};
