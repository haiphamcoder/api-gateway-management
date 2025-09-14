import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useQuery } from '@tanstack/react-query';
import { servicesApi } from '../services/api';
import { PlusIcon, MagnifyingGlassIcon, ArrowPathIcon } from '@heroicons/react/24/outline';
import { AddServiceModal } from '../components/modals/AddServiceModal';
import { AddRouteModal } from '../components/modals/AddRouteModal';
import { DataTable } from '../components/DataTable';

export const ServicesPage: React.FC = () => {
  const { t } = useTranslation();
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isAddRouteModalOpen, setIsAddRouteModalOpen] = useState(false);
  const [selectedServiceId, setSelectedServiceId] = useState<string | null>(null);
  const [search, setSearch] = useState('');

  const { data: servicesData, isLoading, refetch } = useQuery({
    queryKey: ['services', search],
    queryFn: () => servicesApi.getServices({ search: search || undefined }),
  });

  const services = servicesData?.data?.data || [];

  const columns = [
    {
      key: 'name',
      label: t('common.name'),
      render: (service: any) => (
        <div>
          <div className="font-medium text-gray-900">{service.name}</div>
          <div className="text-sm text-gray-500">{service.url}</div>
        </div>
      ),
    },
    {
      key: 'timeouts',
      label: 'Timeouts',
      render: (service: any) => (
        <div className="text-sm text-gray-500">
          <div>Connect: {service.connectTimeout}ms</div>
          <div>Read: {service.readTimeout}ms</div>
          <div>Write: {service.writeTimeout}ms</div>
        </div>
      ),
    },
    {
      key: 'retries',
      label: 'Retries',
      render: (service: any) => (
        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-gray-100 text-gray-800">
          {service.retries}
        </span>
      ),
    },
    {
      key: 'created',
      label: t('common.created'),
      render: (service: any) => (
        <div className="text-sm text-gray-500">
          {new Date(service.createdAt).toLocaleDateString()}
        </div>
      ),
    },
    {
      key: 'actions',
      label: t('common.actions'),
      render: (service: any) => (
        <div className="flex space-x-2">
          <button 
            onClick={() => {
              setSelectedServiceId(service.id);
              setIsAddRouteModalOpen(true);
            }}
            className="text-primary-600 hover:text-primary-900 text-sm font-medium flex items-center"
          >
            <ArrowPathIcon className="h-4 w-4 mr-1" />
            Add Route
          </button>
          <button className="text-blue-600 hover:text-blue-900 text-sm font-medium">
            {t('common.edit')}
          </button>
          <button className="text-red-600 hover:text-red-900 text-sm font-medium">
            {t('common.delete')}
          </button>
        </div>
      ),
    },
  ];

  return (
    <div>
      <div className="sm:flex sm:items-center sm:justify-between mb-8">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">{t('services.title')}</h1>
          <p className="mt-1 text-sm text-gray-500">
            Manage your API services and their configurations
          </p>
        </div>
        <div className="mt-4 sm:mt-0">
          <button
            onClick={() => setIsAddModalOpen(true)}
            className="btn-primary inline-flex items-center"
          >
            <PlusIcon className="h-5 w-5 mr-2" />
            {t('services.addService')}
          </button>
        </div>
      </div>

      {/* Search and filters */}
      <div className="mb-6">
        <div className="relative">
          <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
            <MagnifyingGlassIcon className="h-5 w-5 text-gray-400" />
          </div>
          <input
            type="text"
            placeholder={t('services.searchPlaceholder')}
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md leading-5 bg-white placeholder-gray-500 focus:outline-none focus:placeholder-gray-400 focus:ring-1 focus:ring-primary-500 focus:border-primary-500 sm:text-sm"
          />
        </div>
      </div>

      {/* Services table */}
      <div className="bg-white shadow rounded-lg">
        <DataTable
          data={services}
          columns={columns}
          isLoading={isLoading}
          emptyMessage={t('services.noServices')}
        />
      </div>

      {/* Add Service Modal */}
      <AddServiceModal
        isOpen={isAddModalOpen}
        onClose={() => setIsAddModalOpen(false)}
        onSuccess={() => {
          setIsAddModalOpen(false);
          refetch();
        }}
      />

      {/* Add Route Modal */}
      {selectedServiceId && (
        <AddRouteModal
          isOpen={isAddRouteModalOpen}
          onClose={() => {
            setIsAddRouteModalOpen(false);
            setSelectedServiceId(null);
          }}
          onSuccess={() => {
            setIsAddRouteModalOpen(false);
            setSelectedServiceId(null);
            refetch();
          }}
          serviceId={selectedServiceId}
        />
      )}
    </div>
  );
};
