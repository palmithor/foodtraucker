module.exports = {
    transform: {
        '^.+\\.(js|ts)$': 'ts-jest',
    },
    moduleFileExtensions: ['ts', 'js', 'json'],
    testMatch: ['**/__tests__/**/*.{ts,js}', '**/?(*.)(spec|test).{ts,js}'],
    testPathIgnorePatterns: ['<rootDir>/(node_modules|lib|es|rn|coverage)'],
    mapCoverage: true,
    collectCoverageFrom: ['**/*.{ts,js}', '!**/*.d.ts'],
    globals: {
        'ts-jest': {
            skipBabel: true,
        },
    },
};