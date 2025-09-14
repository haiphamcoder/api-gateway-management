import React from 'react';

interface Column {
    key: string;
    label: string;
    render: (item: any) => React.ReactNode;
}

interface DataTableProps {
    data: any[];
    columns: Column[];
    isLoading?: boolean;
    emptyMessage?: string;
}

export const DataTable: React.FC<DataTableProps> = ({
    data,
    columns,
    isLoading = false,
    emptyMessage = 'No data available',
}) => {
    if (isLoading) {
        return (
            <div className="px-6 py-12 text-center">
                <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600 mx-auto"></div>
                <p className="mt-2 text-sm text-gray-500">Loading...</p>
            </div>
        );
    }

    if (data.length === 0) {
        return (
            <div className="px-6 py-12 text-center">
                <p className="text-sm text-gray-500">{emptyMessage}</p>
            </div>
        );
    }

    return (
        <div className="overflow-hidden">
            <div className="overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                        <tr>
                            {columns.map((column) => (
                                <th
                                    key={column.key}
                                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                                >
                                    {column.label}
                                </th>
                            ))}
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {data.map((item, index) => (
                            <tr key={item.id || `row-${index}-${JSON.stringify(item).slice(0, 50)}`} className="hover:bg-gray-50">
                                {columns.map((column) => (
                                    <td key={column.key} className="px-6 py-4 whitespace-nowrap">
                                        {column.render(item)}
                                    </td>
                                ))}
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};
