import type { Config } from 'tailwindcss';

const config: Config = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      backgroundImage: {
        'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
        'gradient-conic':
          'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))',
      },
      fontFamily: {
        nanum: ['"Nanum Myeongjo"'],
        noto: ['"Noto Sans Korean"'],
        nanumPen: ['"Nanum Pen Script"'],
        melody: ['"Hi Melody"'],
        jalnan: ['Jalnan'],
        jalnanGothic: ['JalnanGothic'],
      },
    },
    screens: {
      f: { min: '2400px' },
      // => @media (max-width: 1279px) { ... }

      e: { max: '2400px' },
      // => @media (max-width: 1279px) { ... }

      d: { max: '1900px' },
      // => @media (max-width: 1279px) { ... }

      c: { max: '1420px' },
      // => @media (max-width: 1023px) { ... }

      b: { max: '1150px' },
      // => @media (max-width: 767px) { ... }

      a: { max: '839px' },
      // => @media (max-width: 639px) { ... }
      f2: {min: '3840px' },
      e2: {max: '3190px' },
      d2: {max: '2550px' },
      c2: {max: '1915px' },
      b2: {max: '1134px' },
      a2: {max: '750px' },
    },
  },
  plugins: [require('tailwind-scrollbar-hide')],
  purge: {
    content: [
      './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
      './src/components/**/*.{js,ts,jsx,tsx,mdx}',
      './src/app/**/*.{js,ts,jsx,tsx,mdx}',
    ],
    options: {
      safelist: [
        'text-xs',
        'text-sm',
        'text-base',
        'text-lg',
        'text-xl',
        'text-2xl',
        'text-3xl',
        'text-4xl',
        'font-nanum',
        'font-noto',
        'font-nanumPen',
        'font-melody',
        'font-jalnan',
        'font-jalnanGothic',
      ],
    },
  },
};
export default config;
